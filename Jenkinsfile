pipeline {

    agent any

    tools {
        maven 'Maven-3.9'
    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/JamesLaurino/fotova-controller.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Run Application') {

            steps {

                withCredentials([

                    string(credentialsId: 'SERVER_HOST', variable: 'SERVER_HOST'),
                    string(credentialsId: 'SERVER_PROTOCOL', variable: 'SERVER_PROTOCOL'),

                    string(credentialsId: 'DB_NAME', variable: 'DB_NAME'),
                    string(credentialsId: 'DB_USERNAME', variable: 'DB_USERNAME'),
                    string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASSWORD'),

                    string(credentialsId: 'JWT_TOKEN', variable: 'JWT_TOKEN'),
                    string(credentialsId: 'JWT_EXP', variable: 'JWT_EXP'),

                    string(credentialsId: 'UPLOAD_FILE', variable: 'UPLOAD_FILE'),
                    string(credentialsId: 'SERVE_PATH', variable: 'SERVE_PATH'),

                    string(credentialsId: 'SENDER_EMAIL', variable: 'SENDER_EMAIL'),

                    string(credentialsId: 'MAIL_HOG_HOST', variable: 'MAIL_HOG_HOST'),
                    string(credentialsId: 'MAIL_HOG_USERNAME', variable: 'MAIL_HOG_USERNAME'),
                    string(credentialsId: 'MAIL_HOG_PASSWORD', variable: 'MAIL_HOG_PASSWORD'),

                    string(credentialsId: 'RABBIT_HOST', variable: 'RABBIT_HOST'),
                    string(credentialsId: 'RABBIT_USERNAME', variable: 'RABBIT_USERNAME'),
                    string(credentialsId: 'RABBIT_PASSWORD', variable: 'RABBIT_PASSWORD'),

                    string(credentialsId: 'STRIPE_KEY', variable: 'STRIPE_KEY')

                ]) {

                    sh '''
                            echo "Copie du jar vers /opt/fotova-app/"
                            cp target/*.jar /opt/fotova-app/
                            echo "Jar copié !"
                    '''

                }

            }

        }

    }
}