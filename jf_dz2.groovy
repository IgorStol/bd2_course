pipeline {
    agent any

    stages {
        stage('Check Disk Space') {
            steps {
                script {
                    // Вывод информации о дисках (Linux/Windows)
                    if (isUnix()) {
                        sh 'df -h'
                    } else {
                        bat 'wmic logicaldisk get size,freespace,caption'
                    }
                }
            }
        }

        stage('Check Top Memory Process') {
            steps {
                script {
                    // Поиск процесса с наибольшим потреблением памяти (Linux/Windows)
                    if (isUnix()) {
                        sh '''
                            echo "Процесс с максимальным потреблением памяти:"
                            ps -eo pid,ppid,cmd,%mem --sort=-%mem | head -n 2
                        '''
                    } else {
                        bat '''
                            echo Список процессов:
                            wmic process get caption,workingsetsize | sort /R | head -n 5
                        '''
                    }
                }
            }
        }
    }
}
