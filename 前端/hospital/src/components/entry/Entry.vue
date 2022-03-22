<template>
    <div>
        <h1 @click="gotoUserLogin" >公众版</h1>
        <h1 @click="gotoWorkesLogin">职工版</h1>
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

<style>

</style>
