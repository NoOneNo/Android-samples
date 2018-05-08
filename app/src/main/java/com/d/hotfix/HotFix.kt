package com.d.hotfix

import android.content.Context
import android.content.pm.PackageManager
import dalvik.system.DexFile
import java.io.*
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.*
import java.lang.reflect.Array.getLength




class HotFix {
    fun init(context: Context){
        val appInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        val cache = File(appInfo.dataDir, "code_cache")
        cache.mkdir()
        val dexDir = File(cache, "auto-dexes")
        dexDir.mkdir()


        val jarDir = File(context.filesDir, "hotfix")
        jarDir.mkdir()
        val jarPath = copyAsset(context, "passport-core.dex", jarDir)

        val files = ArrayList<File>()
        files.add(File(jarPath))

        val loader = context.classLoader
        val pathlistField = findField(loader, "pathList")
        val dexPathList = pathlistField.get(loader)

        val dexElement = findField(dexPathList, "dexElements")
        val elementType  = dexElement.type.componentType
        val loadDex = findMethod(dexPathList, "loadDexFile", File::class.java, File::class.java, ClassLoader::class.java, dexElement.type)
        loadDex.isAccessible = true

        val dex = loadDex.invoke(null, files[0], dexDir, loader, dexElement.get(dexPathList))
        val constrctor = elementType.getConstructor(File::class.java, Boolean::class.java, File::class.java, DexFile::class.java)
        constrctor.isAccessible = true
        val element = constrctor.newInstance(File(""), false, files[0], dex)

        val newEles = Array(1, {return@Array element})
        expandFieldArray(dexPathList, "dexElements", newEles)
    }

//    private fun combineArray(firstArray: Any, secondArray: Any): Any {
//        val localClass = firstArray.javaClass.componentType
//        val firstArrayLength = Array.getLength(firstArray)
//        val allLength = firstArrayLength + Array.getLength(secondArray)
//        val result = Array.newInstance(localClass, allLength)
//        for (k in 0 until allLength) {
//            if (k < firstArrayLength) {
//                Array.set(result, k, Array.get(firstArray, k))
//            } else {
//                Array.set(result, k, Array.get(secondArray, k - firstArrayLength))
//            }
//        }
//        return result
//    }

    @Throws(NoSuchFieldException::class, IllegalArgumentException::class, IllegalAccessException::class)
    fun expandFieldArray(instance: Any, fieldName: String, extraElements: Array<Any>) {

        val jlrField = findField(instance, fieldName)
        val original = jlrField.get(instance) as Array<Any>
        val combined = Array(original.size + extraElements.size, {return@Array extraElements[0]}) as Array<Any>

        System.arraycopy(extraElements, 0, combined, 0, extraElements.size)
        System.arraycopy(original, 0, combined, extraElements.size, original.size)
        jlrField.set(instance, combined)
    }

    @Throws(NoSuchFieldException::class)
    fun findField(instance: Any, name: String): Field {
        var clazz: Class<*>? = instance.javaClass
        while (clazz != null) {
            try {
                val field = clazz.getDeclaredField(name)

                if (!field.isAccessible) {
                    field.isAccessible = true
                }

                return field
            } catch (e: NoSuchFieldException) {
                // ignore and search next
            }

            clazz = clazz.superclass
        }

        throw NoSuchFieldException("Field " + name + " not found in " + instance.javaClass)
    }

    @Throws(NoSuchMethodException::class)
    fun findMethod(instance: Any, name: String, vararg parameterTypes: Class<*>): Method {
        var clazz: Class<*>? = instance.javaClass
        while (clazz != null) {
            try {
                val method = clazz.getDeclaredMethod(name, *parameterTypes)

                if (!method.isAccessible) {
                    method.isAccessible = true
                }

                return method
            } catch (e: NoSuchMethodException) {
                // ignore and search next
            }

            clazz = clazz.superclass
        }

        throw NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes)
                + " not found in " + instance.javaClass)
    }

    @Throws(IOException::class)
    fun copyAsset(context: Context, assetName: String, dir: File): String {
        val outFile = File(dir, assetName)
        if (!outFile.exists()) {
            val assetManager = context.assets
            val inputStream = assetManager.open(assetName)
            val out = FileOutputStream(outFile)
            copyFile(inputStream, out)
            inputStream.close()
            out.close()
        }
        return outFile.absolutePath
    }

    @Throws(IOException::class)
    private fun copyFile(inputStream: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int = inputStream.read(buffer)
        while (read != -1) {
            out.write(buffer, 0, read)
            read = inputStream.read(buffer)
        }
    }
}