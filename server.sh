#!/bin/bash

java -cp 'target/*:target/lib/*' com.hammerspace.jerseyusage.clientserver.Main server $*

exit 0
