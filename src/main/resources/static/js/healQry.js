var app = new Vue({
    el: '#app',
    data: {
        keyword: '',
        info: {
        }
    },
    mounted() {
        axios.get('items', {
            params: {
                page: 1,
                tpCd: 2
            }
        })
            .then(response => (this.info = response.data));
    },
    methods: {
        find: function () {
            axios.get('items', {
                params: {
                    keyword: this.keyword,
                    page: 1,
                    tpCd: 2
                }
            })
                .then(
                    function (response) {
                        app.format(response)}
                )
        },
        page: function (arg) {
            var page = app.info.page + arg;
            page = page > 0 ? page : 1;
            axios.get('items', {
                params: {
                    keyword: this.keyword,
                    page: page,
                    tpCd: 2
                }
            })
                .then(
                    function (response) {
                        app.format(response)}
                );
        },
        format: function (response) {
            //将关键字标红
            var redKeyWord ='<font color="#ff0000">'+app.keyword+'</font>';
            var reg=new RegExp(app.keyword,"g");//构造正则表达式，替换所有 keyword字符串
            app.info = response.data;
            for(var i =0;i<app.info.healInds.length;i++){
                app.info.healInds[i].healCd = app.info.healInds[i].healCd.replace(reg,redKeyWord);
                app.info.healInds[i].healNm = app.info.healInds[i].healNm.replace(reg,redKeyWord);
                app.info.healInds[i].industryCd = app.info.healInds[i].industryCd.replace(reg,redKeyWord);
                app.info.healInds[i].healDesc = app.info.healInds[i].healDesc.replace(reg,redKeyWord);
            }
        }
    }
});
