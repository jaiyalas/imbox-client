# imbox

## Environment 

### Client

Note: [Maven](https://maven.apache.org/) is required

**Building**

    > git clone https://github.com/jaiyalas/imbox.git
    > cd imbox/client
    > mvn clean compile assembly:single

**Executing**

	> java -jar target/Imbox-xxx.jar

### Server


## Repo structure

### Branches

Branch | Description | Major User
------------ | ------------- | ------------
master  | stable system (everything here should work normally) | jaiyalas
release_client | released client | jaiyalas
devel | unstable client | jaiyalas
Ted  | eclipse workspace | teddywinglee
<s>jaiyalas</s> | <s>*abandoned*</s> | <s>jaiyalas</s>


### Directories

Directory | Description | Maintainer
------------ | ------------- | ------------
client/  | *client-side* and *file-related* API  | jaiyalas
eclipse/ | *server-side* and *network-related* API | teddywinglee
server/  | empty | *none*

## License


