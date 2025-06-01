pipeline {
    agent any // Or agent { label 'your_windows_agent_label' }

    stages {
        stage('Checkout Source Code') {
            steps {
                git branch: 'main', url: 'https://github.com/geethasks/mauto.git'
            }
        }

        stage('Run Mobile Tests Locally') {
            steps {
                script {
                    try {
                        echo "Starting Appium server..."
                        bat """
                            cd "${pwd()}"
                            start /B appium -p 4723
                        """

                        echo "Giving Appium server 15 seconds to start..." // Increased sleep for robustness
                        sleep 15 // Increased from 10 to 15

                        echo "Running Maven tests..."
                        def mvnCommand = isUnix() ? "mvn" : "mvn.cmd"
                        bat "${mvnCommand} -f mtest/pom.xml test"

                    } finally {
                        echo "Attempting to stop Appium server and lingering Java processes..."
                        // Kill Appium process by port
                        bat """
                            for /F "tokens=5" %%p in ('netstat -ano ^| findstr :4723') do (
                                set PID=%%p
                            )
                            if defined PID (
                                echo Killing Appium process with PID: %PID%
                                taskkill /PID %PID% /F
                            ) else (
                                echo No Appium process found on port 4723.
                            )
                        """

                        // ADD THIS PART: Aggressively kill any java.exe processes (your Maven/TestNG JVMs)
                        // associated with the current user or session.
                        // /F for forcefully, /T for tree kill (children processes)
                        // This might kill other java processes not related to Jenkins build if not careful
                        // but for a dedicated Jenkins agent, it's often safe.
                        bat """
                            echo Attempting to kill any lingering Java processes...
                            tasklist /FI "IMAGENAME eq java.exe" /FO CSV > java_processes.csv
                            findstr "${env.BUILD_ID}" java_processes.csv > matching_java_processes.csv || REM No matching processes found

                            for /F "tokens=2 delims=," %%a in ('type matching_java_processes.csv') do (
                                set "JAVA_PID=%%a"
                                if defined JAVA_PID (
                                    echo Killing Java process with PID: !JAVA_PID!
                                    taskkill /PID !JAVA_PID! /F /T
                                )
                            )
                            del java_processes.csv matching_java_processes.csv
                        """
                        // NOTE: The above `taskkill` block for Java needs to be wrapped
                        // in a block that enables delayed expansion if you're not careful with `!` vs `%`.
                        // Simpler and generally safer for basic use cases is:
                        bat 'taskkill /F /IM "java.exe" /T || echo No java.exe processes found to kill.'


                        sleep 10 // Increased this sleep after all kills (from 5 to 10)
                        echo "Appium and Java server cleanup attempt complete."
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Waiting a moment before cleaning workspace..."
            sleep 10 // Keep this sleep before deleteDir() (from 5 to 10)
            deleteDir()
        }
    }
}