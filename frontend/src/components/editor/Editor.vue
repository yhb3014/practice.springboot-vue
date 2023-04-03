<template>
    <QuillEditor ref="editor"/>
</template>

<script>
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';

export default {
  components: {
    QuillEditor
  },
  methods: {
    getContents() {
      return this.$refs.editor.getText();
    }
  }
}
</script>

<style>
.ql-toolbar {
    /* background-color: rgba(55, 65, 81, var(--tw-bg-opacity)); */
    background-color: white;
}

.ql-container {
    height: 500px;
}

.ql-editor {
    background-color: rgba(55, 65, 81, var(--tw-bg-opacity));
}

</style>