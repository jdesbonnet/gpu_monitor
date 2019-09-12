
set xdata time
set timefmt "%s"

set format x "%H:%M"

set title "GPU PCIe bus utilization"
set xlabel "Time"
set ylabel "MBytes / s"



plot 'gpu.dat' using 1:($5/1024) title "GPU RX", '' using 1:($6 / 1024) title "GPU TX"

pause -1
