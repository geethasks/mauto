pipeline {
    // You can use 'any' if the Jenkins agent where your device is connected
    // is the default. Or, specify a label if you have multiple agents.
    agent any // e.g., agent { label 'windows_laptop_agent' }

    stages {
        stage('Checkout Source Code') {
            steps {
                git branch: 'main', url: 'https://github.com/geethasks/mauto.git'
            }
        }

        stage('Run Mobile Tests Locally') {
            steps {
                script {
                    // This try-finally block ensures Appium is killed even if tests fail
                    try {
                        echo "Starting Appium server..."
                        // Use 'bat' for Windows batch commands.
                        // 'start /B' runs Appium in the background.
                        // `cd ${pwd()}` ensures the command runs from the workspace root.
                        // Then ensure we return to the current directory
                        // This uses `cmd.exe /C` to make sure `start` is executed correctly.
                        bat """
                            cd ${pwd()}
                            start /B appium -p 4723
                        """

                        echo "Giving Appium server 10 seconds to start..."
                        sleep 10 // Give Appium server time to fully start

                        echo "Running Maven tests..."
                        // Determine Maven command based on OS (for robustness)
                        def mvnCommand = ""
                        if (isUnix()) { // For Linux/macOS agents
                            mvnCommand = "mvn"
                        } else { // For Windows agents
                            mvnCommand = "mvn.cmd" // Use mvn.cmd for Windows
                        }

                        // Execute Maven tests from the mtest subfolder
                        bat "${mvnCommand} -f mtest/pom.xml test"

                    } finally {
                        echo "Attempting to stop Appium server..."
                        // Find and kill the process listening on port 4723
                        // This uses netstat to find the PID and taskkill to terminate it on Windows
                        // This ensures Appium doesn't linger after the job completes
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
                        sleep 2 // Give it a moment to terminate
                        echo "Appium server cleanup attempt complete."
                    }
                }
            }
        }
    }

    // Optional: Post-build actions, e.g., archiving reports or cleaning up workspace
    post {
        always {
            echo "Waiting a moment before cleaning workspace..."
            sleep 5 // <--- ADD THIS SLEEP BEFORE deleteDir()
            // Clean up the workspace after every build (good practice)
            deleteDir()
        }
    }
}