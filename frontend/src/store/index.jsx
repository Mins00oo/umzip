import { configureStore } from "@reduxjs/toolkit"
import testSlice from "./redux_test"
import helps from "./helpRedux"
const store =configureStore({
    reducer:{ test :  testSlice,
    helps : helps.reducer,
    }
})
export default store