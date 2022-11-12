import axios from "axios";

const index = {
    init : function () {
        const _this = this;
        const keywordSettingBtn = document.getElementById("keyword-setting");
        keywordSettingBtn?.addEventListener('submit', (e)=>{
            e.preventDefault();
            _this.saveKeywords();
        });
    },
    saveKeywords : function () {
        const myKeywords = document.getElementsByClassName("my-keyword");
        const arr = [].slice.call(myKeywords);
        let init:{[index:string]: string} = {}
        const params = arr.reduce((acc, cur:HTMLInputElement) => {
            acc[cur.id] = cur.value;
            return acc;
        },init);

        const userEmail = document.getElementById('email') as HTMLInputElement;
        params['userEmail'] = userEmail.value;

        axios.post('/api/v1/keywords', params).then((res) => {
            alert('수정되었습니다.');
            const settingModal = document.getElementById('settingModal');
            settingModal?.classList.add("hidden");
        });
    }
};

index.init();
