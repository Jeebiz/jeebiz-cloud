
1、创建命名空间

kubectl create -f namespace-dev.yaml
kubectl create -f namespace-test.yaml
kubectl create -f namespace-prod.yaml

2、初始化镜像仓库秘钥和Nacos配置（指定命名空间名称）

#kubectl apply -f init-config.yaml --namespace=你的命名空间

kubectl apply -f init-config.yaml --namespace=jeebiz-sample-dev

3、部署/更新应用（指定命名空间名称）

kubectl apply -f jeebiz-cloud-sample-standalone.yaml --namespace=jeebiz-sample-dev
kubectl apply -f jeebiz-cloud-sample-webflux.yaml --namespace=jeebiz-sample-dev
kubectl apply -f jeebiz-cloud-sample-webmvc.yaml --namespace=jeebiz-sample-dev


