let db;
let user;
(function() {
    console.log("config");
    const firebaseConfig = {
        apiKey: "AIzaSyCN3NcDr21moxIuJyY6mY0C6ajHFfp8kkI",
        authDomain: "actuallyanagenda-dae09.firebaseapp.com",
        databaseURL: "https://actuallyanagenda-dae09.firebaseio.com",
        projectId: "actuallyanagenda-dae09",
        storageBucket: "actuallyanagenda-dae09.appspot.com",
        messagingSenderId: "693222778645",
        appId: "1:693222778645:web:6968c5f6c244633cb62b1c",
        measurementId: "G-66LVF1WYBT"
    }
    firebase.initializeApp(firebaseConfig);
    db = firebase.firestore();
    console.log('user');
    firebase.auth().onAuthStateChanged(firebaseUser => {
        if(firebaseUser) {
            user = firebaseUser
            console.log(user.uid);
        } else {
            window.location.href = "index.html";
        }
        showTasks();
        showSchedule();
    });
    console.log('task');
    //setTimeout(showTasks, 300);
}());

function newTask() {
    console.log("new task");
    var name = document.getElementById("txtTask").value;
    var dd = document.getElementById("dateDue").value.split("-");
    var due = new Date(dd[0], dd[1]-1, dd[2], 23, 59, 59, 999);
    console.log(due);
    var priority = parseInt(document.getElementById("priority").value);
    var time = parseInt(document.getElementById("time").value);
    db.collection(user.uid).doc(name).set({
        name: name,
        in: Math.floor(Date.now()/1000),
        ETC: time*3600,
        due: Math.floor(due/1000),
        per: 0,
        priority: priority
    })
    .then(function(doc) {
        console.log("Document written with ID: ", doc.id);
    })
    .catch(function(error) {
        console.error("Error adding document: ", error);
    });
    console.log("access");
    closeForm();
}
function showTasks() {
    var month = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    console.log(user);
    var docRef = db.collection(user.uid).orderBy("priority", "desc");
    docRef.onSnapshot((querySnapshot) => {
        document.getElementById("tasks").innerHTML = "";
        querySnapshot.forEach((doc) => {
            var data = doc.data();
            var due = new Date(data.due*1000);
            console.log(due);
            console.log(`${doc.id} => ${data.name}`);
            document.getElementById("tasks").innerHTML += `<div class="task" style="margin-left: 50px; margin-top: 10px;width: 600px;">
                                                                <h4>${data.name}</h4>
                                                                <p>Due Date: ${month[due.getMonth()]} ${due.getDate()}, ${due.getFullYear()}</p>
                                                                <p>Priority: ${data.priority}</p>
                                                                <p id="val" style="float: none; font-size: 10px;"></p> 
                                                                <input id="percent" type="range" min="0" max="100"; class="slider" style="margin-left: 0px; width: 450px;">
                                                            </div>`;
            document.getElementById("percent").value = data.per;
            slider = document.getElementById("percent");
            output = document.getElementById("val");
            output.innerHTML = `Completion: ${slider.value}%`
            slider.oninput = function() {
                output.innerHTML = `Completion: ${slider.value}%`;
                db.collection(user.uid).doc(doc.id).update({
                    per: slider.value
                });
            }
        });
    });
}

function showSchedule() {
    var month = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    console.log(user);
    var docRef = db.collection(user.uid).orderBy("due");
    docRef.onSnapshot((querySnapshot) => {
        document.getElementById("sched").innerHTML = "";
        querySnapshot.forEach((doc) => {
            var data = doc.data();
            var due = new Date(data.due*1000);
            console.log(due);
            console.log(`${doc.id} => ${data.name}`);
            var dates = "";
            for(var i = 0; i < data.startTime.length; i++) {
                var start = new Date(data.startTime[i]*1000);
                var end = new Date(data.endTime[i]*1000);
                dates += `<p style="margin-top: 6px;">Section ${i+1}: Work from ${start.getHours()}:${start.getMinutes()}, ${month[start.getMonth()]} ${start.getDate()}, ${start.getFullYear()}
                            to ${end.getHours()}:${end.getMinutes()}, ${month[end.getMonth()]} ${end.getDate()}, ${end.getFullYear()}</p>`;
            }
            document.getElementById("sched").innerHTML += `<div class="schedule" style="margin-left: 50px; margin-top: 10px;width: 600px;">
                                                                <h4>${data.name}</h4>
                                                                ${dates}`;
            
        });
    });
}
function openForm() {
    document.getElementById("addTask").style.display = "block";
    $("#fade").css("display", "block");
}
function closeForm() {
    document.getElementById("addTask").style.display = "none";
    $("#fade").css("display", "none");
}