const accountDetails = document.querySelector('#userInfo');
const setupUI = (user) => {
  if (user) {
  // account info
  const html = '<div>'+user.email+'</div>';
  accountDetails.innerHTML = html;
  } else {
    accountDetails.innerHTML = '';
  }
};


