<template>
    <div class="min-h-screen flex justify-center">
        <div class="flex min-h-full items-center justify-center mb-60 px-4 sm:px-6 lg:px-8">
            <div class="w-full max-w-md space-y-8">
                <div>
                    <h2 class="mt-6 text-center text-3xl font-bold tracking-tight text-gray-500">Sign in to your account</h2>
                </div>
                <input type="hidden" name="remember" value="true">
                <div class="-space-y-px rounded-md shadow-sm">
                    <div class="mb-5">
                        <label for="userName" class="block text-left text-sm mb-1">ID</label>
                        <input v-model="userName" type="text" autocomplete="nickname" required class="block w-full appearance-none rounded-md border border-gray-500/30 px-3 py-2 text-sm placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 bg-gray-500 sm:text-base">
                    </div>
                    <div>
                        <label for="password" class="block text-left text-sm mb-1">Password</label>
                        <input v-model="password" type="password" autocomplete="current-password" required class="block w-full appearance-none rounded-md border border-gray-500/30 px-3 py-2 text-sm placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 bg-gray-500 sm:text-base">
                    </div>
                </div>

                <div class="flex items-center justify-between mt-5">
                    <div class="flex items-center">
                    <input id="remember-me" name="remember-me" type="checkbox" class="h-4 w-4 rounded-lg border-gray-300 text-indigo-600 focus:ring-indigo-600">
                    <label for="remember-me" class="ml-2 block text-sm text-indigo-400 hover:text-indigo-300">Remember me</label>
                    </div>

                    <div class="text-sm">
                    <a href="#" class="font-medium text-indigo-400 hover:text-indigo-300">Sign Up</a>
                    </div>
                </div>

                <div>
                    <button type="submit" class="group relative flex w-full justify-center rounded-md bg-indigo-500 py-2 px-3 text-sm font-semibold text-white hover:bg-indigo-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                        v-on:click="signIn">
                    <span class="absolute inset-y-0 left-0 flex items-center pl-3">
                        <svg class="h-5 w-5 text-indigo-500 group-hover:text-indigo-400" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                        <path fill-rule="evenodd" d="M10 1a4.5 4.5 0 00-4.5 4.5V9H5a2 2 0 00-2 2v6a2 2 0 002 2h10a2 2 0 002-2v-6a2 2 0 00-2-2h-.5V5.5A4.5 4.5 0 0010 1zm3 8V5.5a3 3 0 10-6 0V9h6z" clip-rule="evenodd" />
                        </svg>
                    </span>
                    Sign in
                    </button>
                </div>
            </div>
        </div>
    </div>    
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
    data() {
        return {
            userName: '',
            password: ''
        }
    },
    computed: {
        ...mapGetters({
            errorState: 'getErrorState'
        })
    },
    methods: {
        ...mapActions(['login']),

        async signIn() {
            if (this.userName === '') {
                alert("아이디를 입력해주세요.");
                return;
            }

            if (this.password === '') {
                alert("비밀번호를 입력해주세요.");
                return;
            }

            try {
                let loginResult = await this.login({userName: this.userName, password: this.password});
                if (loginResult) {
                    alert(this.userName + " 로그인 성공");
                    this.$router.push({
                        name: "BoardList"
                    })
                }
            } catch (err) {
                alert('로그인 정보를 확인할 수 없습니다.')
            }
        },
    }
}
</script>