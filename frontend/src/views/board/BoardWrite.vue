<template>
    <div class="flex flex-col justify-center min-h-screen mx-52">
        <div class="grid grid-cols-1 gap-y-7">
            <div class="space-y-1">
                <label for="title" class="text-sm font-bold text-gray-300 block text-left">제목</label>
                <input type="text" v-model="title" id="title" placeholder="제목을 입력해주세요." class="block w-full rounded-md border border-gray-600 focus:border-gray-500 focus:outline-none pl-3 pr-10 text-base placeholder-gray-400 shadow-sm bg-gray-700 p-1" name="title">
            </div>
            <div class="space-y-1">
                <label for="text" class="text-sm font-bold text-gray-300 block text-left">본문</label>
                <div class="block text-left">
                    <Editor ref="editor"/>
                </div>
            </div>
        </div>
        <div class="mt-7 flex justify-end">
            <button type="button" class="bg-gray-700 hover:bg-gray-600 duration-300 rounded-md text-sm px-4 py-1.5 mr-5 mb-1" v-on:click="doPost()">등록</button>
            <button type="button" class="bg-gray-700 hover:bg-gray-600 duration-300 rounded-md text-sm px-4 py-1.5 mr-1 mb-1" v-on:click="this.$router.back()">취소</button>
        </div>
    </div>
</template>

<script>
import Editor from "@/components/editor/Editor.vue"

export default {
    components: {
        Editor
    },
    data() {
        return {
            id: this.$route.query.id,
            title: '',
            content: '',
        }
    },
    mounted() {
        this.init();
    },
    methods: {
        doPost() {
            const param = {
                id: this.id,
                title: this.title,
                content: this.$refs.editor.getContents()
            }

            if (this.id === undefined) {
                this.$axios.post("/api/board/doPost", param)
                            .then((res) => {
                                if (res.data.status == 200) {
                                    alert("게시글 등록 성공");
                                } else {
                                    alert("게시글 등록 실패");
                                }
                            }).catch((err) => {
                                console.log(err);
                            })
            } else {
                this.$axios.patch("/api/board/update", param)
                            .then((res) => {
                                if (res.data.status == 200) {
                                    alert("게시글 수정 성공");
                                } else {
                                    alert("게시글 수정 실패");
                                }
                            }).catch((err) => {
                                console.log(err);
                            })
            }
                
            this.$router.push({
                name: "BoardList"
            })

        },

        init() {
            if (this.id !== undefined) {
                this.$axios.get("/api/board/view/" + this.id)
                            .then((res) => {
                                const responseData = res.data.data;
                                this.title = responseData.title;
                                this.$refs.editor.setContents(responseData.content);
                            }).catch((err) => {
                                console.log(err);
                            })
            }
        }
    }
}
</script>