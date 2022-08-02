node {
    stage('拉取代码'){
        checkout([$class: 'GitSCM', branches: [[name: 'main']], extensions: [], userRemoteConfigs: [[url: 'https://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@github.com/csuft-Exceptions/kb-BE.git']]])
    }
    stage('编译,安装kb-common工程'){
        sh "mvn -f kb-common clean install"
    }

    stage('编译,安装kb-file工程'){
            echo "Building kb-file"
            sh "mvn -f kb-file clean install"
            try{
                echo "第一次启动容器kb-file"
                sh "docker run  --name=kb-file -d -p 9009:9009 kb-file-1.0-SNAPSHOT"
            }catch(Throwable e){
                echo "容器kb-file已经启动,现在终止容器运行"
                sh "docker stop kb-file"
                echo "移除容器kb-file"
                sh "docker rm kb-file"
                echo "重新启动容器kb-file"
                sh "docker run  --name=kb-file -d -p 9009:9009 kb-file-1.0-SNAPSHOT"
                echo "清理旧镜像(状态为none的镜像)"
                sh "docker image prune -f"
            }
    }

    stage('编译,安装kb-gateway 工程'){
            echo "Building kb-gateway"
            sh "mvn -f kb-gateway clean install"
            try{
                echo "第一次启动容器kb-gateway"
                sh "docker run  --name=kb-gateway -d -p 9000:9000 kb-gateway-1.0-SNAPSHOT"
            }catch(Throwable e){
                echo "容器kb-gateway 已经启动,现在终止容器运行"
                sh "docker stop kb-gateway"
                echo "移除容器kb-gateway"
                sh "docker rm kb-gateway"
                echo "重新启动容器kb-gateway"
                sh "docker run  --name=kb-gateway -d -p 9000:9000 kb-gateway-1.0-SNAPSHOT"
                echo "清理旧镜像(状态为none的镜像)"
                sh "docker image prune -f"
            }
    }

    stage('编译,安装kb-video 工程'){
            echo "Building kb-video"
            sh "mvn -f kb-video clean install"
            try{
                echo "第一次启动容器kb-video"
                sh "docker run  --name=kb-video -d -p 9010:9010 kb-video-1.0-SNAPSHOT"
            }catch(Throwable e){
                echo "容器kb-video 已经启动,现在终止容器运行"
                sh "docker stop kb-video"
                echo "移除容器kb-video"
                sh "docker rm kb-video"
                echo "重新启动容器kb-video"
                sh "docker run  --name=kb-video -d -p 9010:9010 kb-video-1.0-SNAPSHOT"
                echo "清理旧镜像(状态为none的镜像)"
                sh "docker image prune -f"
            }
    }
    stage('编译,安装kb-user 工程'){
            echo "Building kb-user"
            sh "mvn -f kb-user clean install"
            try{
                echo "第一次启动容器kb-user"
                sh "docker run  --name=kb-user -d -p 9001:9001 kb-user-1.0-SNAPSHOT"
            }catch(Throwable e){
                echo "容器kb-user 已经启动,现在终止容器运行"
                sh "docker stop kb-user"
                echo "移除容器kb-user"
                sh "docker rm kb-user"
                echo "重新启动容器kb-user"
                sh "docker run  --name=kb-user -d -p 9001:9001 kb-user-1.0-SNAPSHOT"
                echo "清理旧镜像(状态为none的镜像)"
                sh "docker image prune -f"
            }
    }
    stage('编译,安装kb-search 工程'){
            echo "Building kb-search"
            sh "mvn -f kb-search clean install"
            try{
                echo "第一次启动容器kb-search"
                sh "docker run  --name=kb-search -d -p 9008:9008 kb-search-1.0-SNAPSHOT"
            }catch(Throwable e){
                echo "容器kb-search 已经启动,现在终止容器运行"
                sh "docker stop kb-search"
                echo "移除容器kb-search"
                sh "docker rm kb-search"
                echo "重新启动容器kb-search"
                sh "docker run  --name=kb-search -d -p 9008:9008 kb-search-1.0-SNAPSHOT"
                echo "清理旧镜像(状态为none的镜像)"
                sh "docker image prune -f"
            }
    }
    stage('编译,安装kb-oauth 工程'){
            echo "Building kb-oauth"
            sh "mvn -f kb-oauth clean install"
            try{
                echo "第一次启动容器kb-oauth"
                sh "docker run  --name=kb-oauth -d -p 9002:9002 kb-oauth-1.0-SNAPSHOT"
            }catch(Throwable e){
                echo "容器kb-oauth 已经启动,现在终止容器运行"
                sh "docker stop kb-oauth"
                echo "移除容器kb-oauth"
                sh "docker rm kb-oauth"
                echo "重新启动容器kb-oauth"
                sh "docker run  --name=kb-oauth -d -p 9002:9002 kb-oauth-1.0-SNAPSHOT"
                echo "清理旧镜像(状态为none的镜像)"
                sh "docker image prune -f"
            }
    }
}
