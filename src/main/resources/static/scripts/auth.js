
// listen for auth status changes
auth.onAuthStateChanged(user => {
if(user) {
    setupUI(user);
} else {
console.log('user logged out');
    setupUI();
}
});


const signupForm = document.querySelector('#signup-form');
signupForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const email = signupForm['signup-email'].value;
    const password = signupForm['signup-password'].value;
    //console.log(email, password);

    //sign up the user
    auth.createUserWithEmailAndPassword(email, password).then(cred => {
        //console.log(cred.user);
        const modal = document.querySelector('#modal-signup');
        M.Modal.getInstance(modal).close();
        signupForm.reset();
    });
});

const createForm = document.querySelector('#create-form');
createForm.addEventListener('submit', (e) => {
    e.preventDefault();
    createForm['first_name'].value;
    createForm['last_name'].value;
    createForm['age'].value;
   createForm['address_line1'].value;
      createForm['address_line2'].value;
   //todo : add here for post call
     M.Modal.getInstance(modal).close();
        createForm.reset();
    });
    

const logout = document.querySelector('#logout');
logout.addEventListener('click', (e) => {
    e.preventDefault();
    auth.signOut().then(() => {
  //      console.log("User logged out");
    });
});


const loginForm = document.querySelector('#login-form');
loginForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const email = loginForm['login-email'].value;
    const password = loginForm['login-password'].value;
    auth.signInWithEmailAndPassword(email, password).then(cred => {
//        console.log(cred.user);
        // close modal login and reset the form
        const modal = document.querySelector('#modal-login');
        M.Modal.getInstance(modal).close();
        loginForm.reset();
    });
});

