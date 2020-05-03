(function() {
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
    const txtEmail = document.getElementById('txtEmail');
    const txtPassword = document.getElementById('txtPassword');
    const btnLogin = document.getElementById('btnLogin');
    const btnSignUp = document.getElementById('btnSignUp');

    btnLogin.addEventListener('click', e => {
        const email = txtEmail.value;
        const pass = txtPassword.value;
        const auth = firebase.auth();
        const promise = auth.signInWithEmailAndPassword(email, pass);
        promise.catch(e => document.getElementById('error').innerHTML = e.message);
    });
    btnSignUp.addEventListener('click', e => {
        const email = txtEmail.value;
        const pass = txtPassword.value;
        const auth = firebase.auth();
        const promise = auth.createUserWithEmailAndPassword(email, pass);
        promise.catch(e => document.getElementById('error').innerHTML = e.message);
    });
    firebase.auth().onAuthStateChanged(firebaseUser => {
        if(firebaseUser) {
            console.log(firebaseUser.displayName);
            window.location.href = "agenda.html";
        } else {
            console.log('not logged in');
        }
    });
}());