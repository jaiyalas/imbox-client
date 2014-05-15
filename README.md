# Imbox

## LINKS:

* [imbox-infra](https://github.com/jaiyalas/imbox-infra)
* [imbox-client](https://github.com/jaiyalas/imbox-client)
* [imbox-client-network](https://github.com/teddywinglee/imbox-client-network)
* [imbox-server](https://github.com/teddywinglee/imbox-server)

## DESCRIPTION:

Imbox is a system to provide a file hosting service. It offers [1] cloud storage, [2] file synchronization, and [3] cross-platform client application.

Imbox consists of two applications: [imbox-client](https://github.com/jaiyalas/imbox-client) and [imbox-server](https://github.com/teddywinglee/imbox-server). They both require [imbox-infra](https://github.com/jaiyalas/imbox-infra) which defines and provides essential data structures and tools. The [imbox-client](https://github.com/jaiyalas/imbox-client) also needs to import [imbox-client-network](https://github.com/teddywinglee/imbox-client-network) to handle connections between client and server.

## REQUIREMENTS:

* Java 8 (tested with Oracle Java 1.8.0_20-ea) 
* Maven 3 (tested with Apache Maven 3.2.1)
* MySQL (server only)

## INSTALLATION:

### Server

To be continued.

### Client

    > git clone https://github.com/jaiyalas/imbox-client.git
    > cd imbox-client/client
    > mvn clean compile assembly:single

## SYNOPSIS:

### Server

To be continued.

### Client

    > java -jar ./target/imbox-client-<version>-jar-with-dependencies.jar

## CONTRIBUTORS:

* [Yun-Yan Chi@(jaiyalas)](https://github.com/jaiyalas)
* [@teddywinglee](https://github.com/teddywinglee)
* [@cindyboy](https://github.com/cindyboy)

## LICENSE:

Apache License 2.0

Copyright (c) 2014, Chi Yun-Yan (jaiyalas)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
