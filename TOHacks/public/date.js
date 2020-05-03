month = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
n = new Date();
y = n.getFullYear();
m = month[n.getMonth()];
d = n.getDate();
document.getElementById("date").innerHTML = m + " " + d + ", " + y;