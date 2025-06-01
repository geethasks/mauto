pipeline {
    agent any

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
                        echo "--- STARTING APPIUM ---"
                        bat """
                            cd "${pwd()}"
                            start /B appium -p 4723
                        """
                        echo "Appium launch command issued. Giving 15 seconds to start..."
                        sleep 15

                        echo "--- RUNNING MAVEN TESTS ---"
                        def mvnCommand = isUnix() ? "mvn" : "mvn.cmd"
                        bat "${mvnCommand} -f mtest/pom.xml test"
                        echo "Maven tests command completed."

                    } finally {
                        echo "--- STARTING CLEANUP IN FINALLY BLOCK ---"

                        echo "Attempting to kill Appium process by port 4723..."
                        bat """
                            for /F "tokens=5" %%p in ('netstat -ano ^| findstr :4723') do (
                                set PID=%%p
                            )
                            if defined PID (
                                echo Found Appium PID: %PID%. Killing now...
                                taskkill /PID %PID% /F
                            ) else (
                                echo No Appium process found on port 4723.
                            )
                        """
                        echo "Appium kill command issued. Waiting 5 seconds for OS to process..."
                        sleep 5

                        echo "Attempting to kill any lingering Java processes (Maven/TestNG JVMs)..."
                        // This command attempts to kill all Java processes forcibly.
                        // Add `cmd /C` to ensure the command is executed and completes within the bat context.
                        bat 'cmd /C "taskkill /F /IM "java.exe" /T || echo No java.exe processes found to kill."'
                        echo "Java kill command issued. Waiting 5 seconds for OS to process..."
                        sleep 5

                        echo "All process cleanup commands issued. Final short sleep before finally block exits."
                        sleep 5 // Short sleep before exiting the 'finally' block
                        echo "--- CLEANUP SEQUENCE COMPLETE ---"
                    }
                }
            }
        }
    }

    post {
        always {
            echo "--- ATTEMPTING WORKSPACE CLEANUP ---"
            echo "Waiting 10 seconds before attempting deleteDir()..."
            sleep 10 // Give a final chance for resources to release
            deleteDir()
            echo "--- WORKSPACE CLEANUP COMPLETE ---"
        }
    }
}