var app = new Vue({
  el: '#app',
  data: {
    keyword: '',
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
  },
    methods:{
        cancelFun: function(){
            document.getElementById("frameId").style.display = "none";
        },
        sureFun: function(index){
            axios.get('reload').then(response => (this.info = response.data));
            document.getElementById("frameId").style.display = "none";

        },
        reLoadFun: function(index){
            document.getElementById("frameId").style.display = "block";
        },
        find: function() {
            axios.get('items', {
                 params: {
                   q: this.keyword,
                   p: 1
                 }
             })
             .then(response => (this.info = response.data));
        },
        page: function(arg) {
             var page = app.info.page + arg;
             page = page > 0 ? page : 1;
             axios.get('items', {
                  params: {
                    q: this.keyword,
                    p: page
                  }
              })
              .then(response => (this.info = response.data));
        }
    }
});
