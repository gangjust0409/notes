import { ref } from 'vue'
import userRequest from '@/api/user.js'

export default function () {

    //关注
    async function attention(data) {
        const res = await userRequest.attention(data);
        if (res.code === 200) {
            return res;
        }
    }

}

