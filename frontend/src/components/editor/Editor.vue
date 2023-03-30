<template>
  <SummernoteEditor
      v-model="myValue"
      @update:modelValue="summernoteChange($event)"
      @summernoteImageLinkInsert="summernoteImageLinkInsert"
    />
</template>
<script>
import SummernoteEditor from 'vue3-summernote-editor';

export default {
    // declare SummernoteEditor
    components: {
      SummernoteEditor
    },
    data() {
        return {
            myValue: '',
        }
    },
    methods: {
       summernoteChange(newValue) {
          console.log("summernoteChange", newValue);
       },
        summernoteImageLinkInsert(...args) {
          console.log("summernoteImageLinkInsert()", args);
       },
    }
}
</script>