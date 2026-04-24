def services = [
    [name: 'kb-file',    port: '9009'],
    [name: 'kb-gateway', port: '9000'],
    [name: 'kb-video',   port: '9010'],
    [name: 'kb-user',    port: '9001'],
    [name: 'kb-search',  port: '9008'],
    [name: 'kb-oauth',   port: '9002']
]

node {
    stage('Checkout') {
        checkout([
            $class: 'GitSCM',
            branches: [[name: '*/main']],
            userRemoteConfigs: [[url: 'https://github.com/csuft-Exceptions/kb-BE.git']]
        ])
    }

    stage('Build kb-common') {
        sh 'mvn -f kb-common clean install -Dmaven.test.skip=true'
    }

    services.each { svc ->
        stage("Build & Deploy ${svc.name}") {
            sh "mvn -f ${svc.name} clean install -Dmaven.test.skip=true"
            sh """
                docker rm -f ${svc.name} || true
                docker run --name=${svc.name} -d -p ${svc.port}:${svc.port} ${svc.name}:1.0-SNAPSHOT
                docker image prune -f
            """
        }
    }
}
