To reproduce the problem:
=========================
$ terminal1> git clone <this repo>
$ terminal1> cd jerseybug/
$ terminal1> mvn clean install
$ terminal1> ./server.sh 1024

From another terminal:
----------------------
$ terminal2> cd jerseybug/

$ terminal2> ./client.sh sync
<notice that this client prints the status and content of the Response from the server>

$ terminal2> ./client.sh async
<notice that this client hangs after parsing the status of the Response>


This issue does not happen with the sync nor the async client if the server's response payload is "small":
$ terminal1> ./server.sh 64

$ terminal2> ./client sync
<prints Response; no hang>
$ terminal2> ./client aync
<prints Response; no hang>

