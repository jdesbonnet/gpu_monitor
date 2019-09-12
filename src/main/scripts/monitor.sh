#!/bin/bash
# Use nvidia-smi to record GPU information every INTERVAL seconds. 
# Output to XML file with timestamp in filename.
INTERVAL=5
while true ; do
	ts=`date +%s`
	file="gpu-info-$ts.xml"
	echo "writing $file"
	nvidia-smi -q -x > $file
	sleep $INTERVAL
done
