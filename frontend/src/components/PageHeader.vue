<template>
    <header class="sticky w-full top-0 z-20 bg-gray-800 border-b border-gray-600">
        <div class="py-8 px-6 flex justify-center">
            <div class="">
                <router-link to="/" class=" hover:bg-gray-600 duration-300 rounded-md p-3 mx-5">Home</router-link>
                <router-link to="/board/list" class=" hover:bg-gray-600 duration-300 rounded-md p-3 mx-5">Board</router-link>
                <router-link to="/user/signIn" class=" hover:bg-gray-600 duration-300 rounded-md p-3 mx-5" v-if="!this.$store.state.isLogin">Sign In</router-link>
                <a class=" hover:bg-gray-600 duration-300 rounded-md p-3 mx-5" v-else v-on:click="signOut()">Sign Out</a>
                <router-link to="/user/signUp" class=" hover:bg-gray-600 duration-300 rounded-md p-3 mx-5" v-if="!this.$store.state.isLogin">Sign Up</router-link>
            </div>
        </div>
    </header>
</template>

<script>
export default {
    methods: {
        signOut() {
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");
            localStorage.removeItem("grantType");
            location.reload();
        }
    }
}
</script>