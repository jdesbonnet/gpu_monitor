
set xdata time
set timefmt "%s"

set format x "%H:%M"

set title "GPU power / thermal"
set xlabel "Time"
set ylabel ""



plot 'gpu.dat' using 1:7 title "GPU core temperature C", '' using 1:9 title "power watts"

pause -1
