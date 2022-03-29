<template>
<div>
    <div id="user" @click="gotoUserLogin">
       <h1 >用户</h1>
    </div>
     <div id="work"  @click="gotoWorkesLogin">
      <h1>职工</h1>
    </div>
    </div>
</template>

<script>
export default {
   data() {
      return {

      }
   },
   created(){

   },
   methods:{
       gotoUserLogin(){
           this.$router.push('/gotoUserLogin')
       },
       gotoWorkesLogin(){
           this.$router.push('/gotoWorkesLogin')
       }
   }
}
</script>

<style  scoped>
h1 {
     height: 90%; 
    width:100%;
    display:flex;
    align-items: center;
    justify-content: center;
  color: #95CACA;
}
#user {
  width: 50%;
  height: 100vh;
  background: url("../../assets/css/image/星空.png") center center no-repeat;
  float: left;
   background-size: 100% 100%;
  /* position: fixed;  */
}
#work {
  width: 50%;
  height: 100vh;

  background: url("../../assets/css/image/用户界面.jpg")center center no-repeat ;
   float: right;
  background-size: 100% 100%;
  /* position: fixed; */
}
</style>
