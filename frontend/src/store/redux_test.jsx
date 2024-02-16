import { createSlice } from "@reduxjs/toolkit";

const testSlice = createSlice({
    name:"test",
    initialState: {count : 0},
    reducers:{
        addcount(state,action){
            state.count++
        }
    }
})
export const testAction = testSlice.actions
export default testSlice.reducer