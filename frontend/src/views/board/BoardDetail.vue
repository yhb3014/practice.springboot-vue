<template>
    <div class="my-20 min-h-screen mx-52">
        <div class="grid grid-cols-1 gap-y-7">
            <div class="flex">
                <div class="space-y-1">
                    <label for="title" class="text-sm font-bold text-gray-300 block text-left p-1 mr-3">작성자</label>
                    <div class="block text-left underline rounded-lg">
                        <div class="p-3">
                            {{ userName }}
                        </div>
                    </div>
                </div>
                <div class="space-y-1">
                    <label for="title" class="text-sm font-bold text-gray-300 block text-left p-1 mr-3">작성일자</label>
                    <div class="block text-left underline rounded-lg">
                        <div class="p-3">
                            {{ createDate }}
                        </div>
                    </div>
                </div>
                <div class="space-y-1">
                    <label for="title" class="text-sm font-bold text-gray-300 block text-left p-1">조회수</label>
                    <div class="block text-left underline rounded-lg">
                        <div class="p-3">
                            {{ count }}
                        </div>
                    </div>
                </div>
            </div>
            <div class="space-y-1">
                <label for="title" class="text-sm font-bold text-gray-300 block text-left p-1">제목</label>
                <div class="block text-left bg-gray-700 rounded-lg">
                    <div class="p-3">
                        {{ title }}
                    </div>
                </div>
            </div>
            <div class="space-y-1">
                <label for="text" class="text-sm font-bold text-gray-300 block text-left p-1">본문</label>
                <div class="min-h-full bg-gray-700 rounded-lg">
                    <div class="block text-left p-3">
                        <p v-html="content"></p>
                    </div>
                </div>
            </div>
            <div class="my-7 flex justify-end">
                <button type="button" class="bg-blue-700 hover:bg-blue-600 duration-300 rounded-md text-sm px-4 py-1.5 mr-5 mb-1" v-if="this.$store.getters.getUserName === userName" v-on:click="updateBoard(id)">수정</button>
                <button type="button" class="bg-red-700 hover:bg-red-600 duration-300 rounded-md text-sm px-4 py-1.5 mr-5 mb-1" v-if="this.$store.getters.getUserName === userName" v-on:click="deleteBoard(id)">삭제</button>
                <button type="button" class="bg-gray-700 hover:bg-gray-600 duration-300 rounded-md text-sm px-4 py-1.5 mr-1 mb-1" v-on:click="this.$router.back()">목록</button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            id: this.$route.params.id,
            userName: '',
            title: '',
            content: '',
            createDate: '',
            count: ''
        }
    },
    mounted() {
        this.getBoardDetail();
    },
    methods: {
        getBoardDetail() {
            this.$axios.get("/api/board/view/" + this.id)
                        .then((res) => {
                            const responseData = res.data.data;
                            this.userName = responseData.userName;
                            this.title = responseData.title;
                            this.content = responseData.content.replaceAll(/(\n|\r\n)/g,'<br>');
                            this.createDate = responseData.createDate;
                            this.count = responseData.count;
                        }).catch((err) => {
                            console.log(err);
                        })
        },

        updateBoard(id) {
            this.$router.push({
                name: "BoardWrite",
                query: {
                    id: id
                }
            })
        },

        deleteBoard(id) {
            this.$axios.post("/api/board/delete/" + id)
                        .then((res) => {
                            if (res.status == 200) {
                                alert("게시글 삭제 성공");
                                this.back()
                            } else {
                                alert("게시글 삭제 실패");
                            }
                        }).catch((err) => {
                            console.log(err);
                        })
        },
    }
}
</script>