// listen for auth status changes
auth.onAuthStateChanged(user => {
if(user) {
  
} else {
console.log('user logged out');
  
}
});


const signupForm = document.querySelector('#signup-form');
if(signupForm) {
signupForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const email = signupForm['signup-email'].value;
    const password = signupForm['signup-password'].value;
    //console.log(email, password);

    //sign up the user
    auth.createUserWithEmailAndPassword(email, password).then(cred => {
        //console.log(cred.user);
        window.location.replace('home.html');   
    });
});
}

const logout = document.querySelector('#logout');
if(logout){
logout.addEventListener('click', (e) => {
    e.preventDefault();
    auth.signOut().then(() => {
  	window.location.replace('index.html');
    });
});
}

const loginForm = document.querySelector('#login-form');
if(loginForm) {
loginForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const email = loginForm['login-email'].value;
    const password = loginForm['login-password'].value;
    auth.signInWithEmailAndPassword(email, password).then(cred => {
 	window.location.replace('home.html');   
 	});
 	
});
}
