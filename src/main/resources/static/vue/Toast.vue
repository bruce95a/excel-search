<template>
    <div class="toast" :class="{ac:acModal}" :style="{'bottom':bottom}">{{title}}</div>
</template>

<script>

    export default {
        data:function(){
            return{
                acModal:false,
            }
        },
        props:{
            title:String,
            bottom:{
                type: String,
                default: "0px"
            }
        },
        methods:{
            childAction(){
                let that = this;
                that.acModal = true;
                setTimeout(()=>{
                    that.acModal = false;
                },3000)
            }
        },
        mounted(){
            this.$on('bridge',()=>{
                this.childAction();
            });
        }
    }
</script>
<style lang="scss"  scoped>
    .toast{
        display: none;
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
        background-color: #000;
        opacity: 0.7;
        padding:0 25px;
        height: 48px;
        font-size: 14px;
        line-height: 48px;
        color: #fff;
        border-radius: 3px;
        z-index: 10;
    }
    .ac{
        display: block;
    }
</style>