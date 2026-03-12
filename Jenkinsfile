pipeline {

    agent any

    tools {
        maven 'Maven-3.9'
    }

    environment {

        SERVER_HOST = "${SERVER_HOST}"
        SERVER_PROTOCOL = "${SERVER_PROTOCOL}"

        DB_NAME = "${DB_NAME}"
        DB_USERNAME = "${DB_USERNAME}"
        DB_PASSWORD = "${DB_PASSWORD}"

        JWT_TOKEN = "${JWT_TOKEN}"
        JWT_EXP = "${JWT_EXP}"

        UPLOAD_FILE = "${UPLOAD_FILE}"
        SERVE_PATH = "${SERVE_PATH}"

        SENDER_EMAIL = "${SENDER_EMAIL}"

        MAIL_HOG_HOST = "${MAIL_HOG_HOST}"
        MAIL_HOG_USERNAME = "${MAIL_HOG_USERNAME}"
        MAIL_HOG_PASSWORD = "${MAIL_HOG_PASSWORD}"

        RABBIT_HOST = "${RABBIT_HOST}"
        RABBIT_USERNAME = "${RABBIT_USERNAME}"
        RABBIT_PASSWORD = "${RABBIT_PASSWORD}"

        STRIPE_KEY = "${STRIPE_KEY}"
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
                sh '''
                java -jar target/*.jar
                '''
            }
        }

    }
}