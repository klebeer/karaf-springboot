pipeline {
    agent any

    environment {
        scmUrl = 'https://github.com/klebeer/karaf-springboot.git'
    }

    parameters {
        choice(name: 'releseChoice',
                choices: 'Deploy\nRelease Version',
                description: 'What option do you choose?')

        booleanParam(name: 'junit',
                defaultValue: false,
                description: 'Run Unit Tests')

        booleanParam(name: 'sonarqube',
                defaultValue: false,
                description: 'SonarQube')


        string(name: 'branchName',
                defaultValue: 'karaf-springboot-1.0.3',
                description: 'The branch to build')


    }

    stages {

        stage('Branch List') {
            when {
                expression {
                    return params.branchName == '';
                }
            }
            steps {
                script {

                    git credentialsId: 'devnull', url: scmUrl
                    def branchFileExist = fileExists 'branch.txt'

                    if (branchFileExist) {
                        echo "branch list exist... deleting"
                        sh 'rm -f branch.txt'
                    }


                    git credentialsId: 'devnull', url: scmUrl
                    sh 'git branch -r | awk \'{print $1}\' ORS=\'\\n\' | sed \'s/origin\\// /g\' >>branch.txt'

                    liste = readFile 'branch.txt'

                    echo "Please click on the link here to chose the branch to build"
                    branch = input message: 'Please choose the branch to build ', ok: 'OK',
                            parameters: [choice(name: 'BRANCH_NAME', choices: "${liste}", description: 'Branch to build?')]

                    echo "Branch selected ${params.branchName}"

                }

            }
        }

        stage("Checkout") {
            steps {
                script {
                    if (params.branchName != '') {
                        echo "Using Branch from parameter:  ${params.branchName}"
                        branch = params.branchName
                    } else {

                    }

                    echo "working with branch: ${branch}"

                    git credentialsId: 'devnull', url: scmUrl

                    sh "git checkout ${branch} && git reset --hard origin/${branch}"
                }
            }
        }

        stage('Tests') {
            when {
                expression {
                    return params.junit;
                }
            }
            steps {
                script {
                    try {

                        withEnv(["JAVA_HOME=${tool 'JDK8'}", "PATH+MAVEN=${tool 'M3'}/bin:${env.JAVA_HOME}/bin"]) {
                            withMaven(
                                    maven: 'M3',
                                    mavenSettingsConfig: 'nexus-devnull', mavenOpts: '-Xmx1024m -XX:+TieredCompilation -XX:TieredStopAtLevel=1') {

                                sh 'mvn clean install  -Dmaven.test.failure.ignore=true'
                            }
                        }

                    } finally {
                        junit testResults: 'tests/bobcat/target/*.xml', allowEmptyResults: true
                        archiveArtifacts '**/target/**'
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            when {
                expression {
                    return params.sonarqube;
                }
            }
            steps {

                withEnv(["JAVA_HOME=${tool 'JDK8'}", "PATH+MAVEN=${tool 'M3'}/bin:${env.JAVA_HOME}/bin"]) {
                    withSonarQubeEnv('Sonar') {
                        sh 'mvn  sonar:sonar'
                    }
                }

            }
        }
        stage('Deploy') {
            when {
                expression {
                    return params.releseChoice == 'Deploy';
                }
            }
            steps {

                withEnv(["JAVA_HOME=${tool 'JDK8'}", "PATH+MAVEN=${tool 'M3'}/bin:${env.JAVA_HOME}/bin"]) {
                    withMaven(
                            maven: 'M3',
                            mavenSettingsConfig: 'nexus-devnull', mavenOpts: '-Xmx1024m -XX:+TieredCompilation -XX:TieredStopAtLevel=1') {
                        sh "mvn clean install"
                        sh "mvn clean deploy -DskipTests "
                    }
                }

            }

        }

        stage('Release') {
            when {
                expression {
                    return params.releseChoice == 'Release Version';
                }
            }
            steps {
                script {
                    def lastCommit = sh returnStdout: true, script: 'git log -1 --pretty=%B'

                    if (lastCommit.contains("[maven-release-plugin]")) {
                        sh "echo  Maven release detected"

                    } else {

                        sh "echo Last commit is not from maven release plugin"
                        withEnv(["JAVA_HOME=${tool 'JDK8'}", "PATH+MAVEN=${tool 'M3'}/bin:${env.JAVA_HOME}/bin"]) {
                            withMaven(
                                    maven: 'M3',
                                    mavenSettingsConfig: 'nexus-devnull', mavenOpts: '-Xmx1024m -XX:+TieredCompilation -XX:TieredStopAtLevel=1') {

                                sh "git config user.email jenkins@modinter.com.ec && git config user.name Jenkins"
                                sh "mvn clean install"
                                sh "mvn -DskipTests -Darguments=-DskipTests release:prepare release:perform "
                            }
                        }
                    }


                }
            }

        }
        stage('Clean') {
            steps {

                withMaven(
                        maven: 'M3',
                        mavenSettingsConfig: 'nexus-devnull', mavenOpts: '-Xmx1024m') {
                    sh "mvn clean"
                }

            }
        }
    }
}