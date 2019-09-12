
set xdata time
set timefmt "%s"

set format x "%H:%M"

set title "GPU and GPU memory utilization"
set xlabel "Time"
set ylabel "Utilization %"


set yrange [0:100]

plot 'gpu.dat' using 1:3 title "GPU utilization", '' using 1:4 title "Memory utilization"

pause -1
