<template>
  <div id="home">


    <div id="user" @click="gotoUserLogin">
      <h1 @click="gotoUserLogin">用户请登入</h1>
    </div>
    <div id="work" @click="gotoWorkesLogin">
      <h1 @click="gotoWorkesLogin">职工请登入</h1>
    </div>


    <img src="../../assets/css/image/前台首页1.png">
    <img src="../../assets/css/image/前台首页2.png">
    <div id="user" @click="gotoUserLogin">
      <h1 @click="gotoUserLogin" style="color:white">s</h1>
    </div>
    <div id="work" @click="gotoWorkesLogin">
      <h1 @click="gotoWorkesLogin" style="color:white">s</h1>

    </div>
    <img src="../../assets/css/image/前台首页3.png">
    <img src="../../assets/css/image/前台首页4.png">

  </div>
</template>

<script>
  export default {
    data() {
      return {

      }
    },
    created() {

    },
    methods: {
      gotoUserLogin() {
        this.$router.push('/gotoUserLogin')
      },
      gotoWorkesLogin() {
        this.$router.push('/gotoWorkesLogin')
      }
    }
  }

</script>
<style scoped>
  h1 {
    height: 90%;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #5d0093;
  }

  #user {
    /* width: 50%; */
    height: 100vh;

    float: left;
    background-size: 100% 100%;
    /* position: fixed;  */
  }

  #work {
    /* width: 50%; */
    height: 100vh;


    float: right;
    background-size: 100% 100%;
    /* position: fixed; */
  }

</style>
