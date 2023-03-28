<template>
    <div class="mx-60 my-10 min-h-screen">
      <div class="flex justify-end space-x-2 m-2">
        <button type="button" class="bg-gray-700 hover:bg-gray-600 duration-300 rounded-md text-sm px-4 py-1.5 mr-1 mb-1" v-on:click="doPost">등록</button>
      </div>
      <div class="flex flex-col">
        <div class="overflow-x-auto sm:-mx-6 lg:-mx-8">
          <div class="inline-block min-w-full py-2 sm:px-6 lg:px-8">
            <div class="overflow-hidden">
              <table class="min-w-full text-sm font-light">
                <thead class="border-b border-gray-500 bg-gray-700">
                  <tr>
                    <th scope="col" class="px-6 py-4">No</th>
                    <th scope="col" class="px-6 py-4">제목</th>
                    <th scope="col" class="px-6 py-4">작성자</th>
                    <th scope="col" class="px-6 py-4">등록일시</th>
                    <th scope="col" class="px-6 py-4">조회수</th>
                  </tr>
                </thead>
                <tbody>
                  <tr class="border-b border-gray-500 transition duration-300 hover:bg-gray-700 cursor-pointer"
                  v-for="list in boardList" :key="list.id">
                    <td class="whitespace-nowrap px-6 py-4 font-medium">{{ list.id }}</td>
                    <td class="whitespace-nowrap px-6 py-4"><a v-on:click="getBoardContent(`${list.id}`)">{{ list.title }}</a></td>
                    <td class="whitespace-nowrap px-6 py-4">{{ list.user }}</td>
                    <td class="whitespace-nowrap px-6 py-4">{{ list.createDate }}</td>
                    <td class="whitespace-nowrap px-6 py-4">{{ list.count }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() { // 변수생성
      return {
        requestBody: {}, // 리스트 페이지 데이터전송
        boardList: {}, // 리스트 데이터
      }
    },
    mounted() {
      this.getBoardList()
    },
    methods: {
      getBoardList() {
          this.$axios
              .get("/api/board/list")
              .then((res) => {
                  this.boardList = res.data
                  console.log(res)
              }).catch((err) => {
                  console.log(err)
            })
        }
      }
  }
  </script>