#!/bin/bash
while true ; do
	ts=`date +%s`
	file="gpu-info-$ts.xml"
	echo "writing $file"
	nvidia-smi -q -x > $file
	sleep 5
done
