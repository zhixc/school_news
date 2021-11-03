<template>
  <div class="home">
    <Header/>
    <Container/>
    <Footer/>
  </div>
</template>

<style>
  .home{

  }
</style>

<script>
  import Header from "../../components/common/Header";
  import Footer from "../../components/common/Footer";
  import Container from "./Container";

export default {
  name: 'Home',
  components: {
    Header,
    Container,
    Footer
  }
}
</script>
