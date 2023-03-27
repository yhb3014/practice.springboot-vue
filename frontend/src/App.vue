<template>
  <div>
    <PageHeader />
    <router-view />
    <PageFooter />
  </div>
</template>

<script>
import PageHeader from "@/components/PageHeader"
import PageFooter from "@/components/PageFooter"

export default {
 components: {
  PageHeader,
  PageFooter,
 },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: white;
}

</style>
