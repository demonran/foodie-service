def getHost(){
    def remote = [:]
    remote.name = 'mysql'
    remote.host = '192.168.8.108'
    remote.user = 'root'
    remote.port = 22
    remote.password = 'qweasd'
    remote.allowAnyHosts = true
    return remote
}
pipeline {
    agent {
        node {
            label 'host-local'
        }
    }
    stages {
        stage('Git') {
            steps {
                git 'https://github.com/demonran/foodie-service.git'
            }
        }
        stage('Build') {
            steps {
                withEnv(['JENKINS_NODE_COOKIE=dontkillme']) {
                    sh './gradlew --no-daemon clean build'
                }
            }
        }

        stage('Deploy') {
            steps {
                 sh '\\cp ./build/libs/*.jar /opt/'
                 sh '\\cp ./run.sh /opt/'
                 withEnv(['JENKINS_NODE_COOKIE=dontkillme']) {
                    sh '/opt/run.sh /opt/foodie-service-0.0.1-SNAPSHOT.jar restart'
                 }
            }
        }
    }
}
