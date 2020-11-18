var app = new Vue({
  el: '#app',
  data: {
    info: {
        items: [{
            batch: '',
            industry: '',
            category: '',
            health: '',
            code: '',
            name: '',
            scope: '',
            logout: '',
            status: ''
        }],
        total: 1, // total page
        page: 1, // page index
        size: 1 // page size
    }
  },
  mounted() {
    axios.get('items').then(response => (this.info = response.data));
  }
});

function w3_open() {
    document.getElementById("mySidebar").style.display = "block";
    document.getElementById("myOverlay").style.display = "block";
}

function w3_close() {
    document.getElementById("mySidebar").style.display = "none";
    document.getElementById("myOverlay").style.display = "none";
}

function reload() {
 console.log('reload from excel');
 axios.get('reload').then(response => (app.info = response.data));
}

function find() {
 console.log('search');
 axios.get('items', {
     params: {
       k: '',
       i: 1
     }
 })
 .then(response => (app.info = response.data));
}

function page(arg) {
 console.log('page');
 console.log(arg);
}