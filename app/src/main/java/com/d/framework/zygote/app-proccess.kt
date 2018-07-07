package com.d.framework.zygote



// Process.start

// Process.startViaZygote

// Process.zygoteSendAndGetPid

    // openZygoteSocketIfNeeded -> sZygoteWriter Socket写入流


// =======

// ZygoteInit.runSelectLoopMode 监听socket

    // peers.get(index).runOnce()  -- peers.get(index)得到的是一个ZygoteConnection对象，表示一个Socket连接

    // ZygoteConnection.runOnce

        // Zygote.forkAndSpecialize

        // ZygoteConnection.handleChildProc

            // RuntimeInit.zygoteInit

                // RuntimeInit.zygoteInitNative

                    // AppRuntime.onZygoteInit

                        // ProcessState.startThreadPool -- 启动线程池了，这个线程池中的线程就是用来和Binder驱动程序进行交互的

                        // ProcessState.spawnPooledThread

                        // PoolThread.threadLoop -- IPCThreadState也是Binder进程间通信机制的一个基础组件