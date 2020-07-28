function populatePatientData(patients) {
 document.getElementById("firstName").value = patients.firstName;
 document.getElementById("lastName").value = patients.lastName;
 document.getElementById("email").value = patients.email;
  var ele = document.getElementById('gender'); 
            for(i = 0; i < ele.length; i++) { 
                if(ele[i] == patients.gender) {
                	ele[i].checked  =true;
                } 
                
            } 
  document.getElementById("phone").value = patients.phone;
  document.getElementById("address1").value = patients.addressLine1;
    document.getElementById("address2").value = patients.addressLine2;
    document.getElementById("city").value = patients.city;
       document.getElementById("state").value = patients.state;
       document.getElementById("country").value = patients.country;
       document.getElementById("pincode").value = patients.pincode;}

function updatePatientData() {
const data = JSON.stringify({
  name: 'Roger',
  age: 8
})

const xhr = new XMLHttpRequest()
xhr.withCredentials = true

xhr.addEventListener('readystatechange', function() {
  if (this.readyState === this.DONE) {
    console.log(this.responseText)
  }
})

xhr.open('POST', 'http://localhost:8080/patients')
xhr.setRequestHeader('content-type', 'application/json')
xhr.setRequestHeader('authorization', 'Bearer 123abc456def')

xhr.send(data)
}

function addPatientData() {
const data = JSON.stringify({
  name: 'Roger',
  age: 8
})

const xhr = new XMLHttpRequest()
xhr.withCredentials = true

xhr.addEventListener('readystatechange', function() {
  if (this.readyState === this.DONE) {
    console.log(this.responseText)
  }
})

xhr.open('POST', '')
xhr.setRequestHeader('content-type', 'application/json')
xhr.setRequestHeader('authorization', 'Bearer 123abc456def')

xhr.send(data)
}

