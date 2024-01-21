import {
    Wrap,
    WrapItem,
    Card,
    Spinner,
    Text
} from "@chakra-ui/react";
import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {useEffect} from "react";
import {getCustomers} from "./services/client.js";
import {useState} from "react";
import CardWithImage from "./components/Card.jsx";
import DrawerForm from "./components/CreateCustomerDrawer.jsx";
import {errorNotification} from "./services/notification.js";
import CreateCustomerDrawer from "./components/CreateCustomerDrawer.jsx";

const App = () => {

    const [customers, setCustomers] = useState([]);

    const [loading, setLoading] = useState(false);

    const [err, setErr] = useState("");

    const fetchCustomers = () => {
        setLoading(true);
        getCustomers().then(res => {
            setCustomers(res.data)
        }).catch(err => {
            setErr(err.response.data.message)
            errorNotification(
                err.code,
                err.response.data.message
            )
        }).finally(() => {
            setLoading(false);
        })
    }

    useEffect(() => {
        fetchCustomers();
    }, [])

    if (loading) {
        return (
            <SidebarWithHeader>
                <Spinner
                    thickness={"4px"}
                    speed={"0.65s"}
                    emptyColor={"gray.200"}
                    color={"blue.500"}
                    size={"xl"}
                />

            </SidebarWithHeader>
        )
    }

    if (err) {
        return (
            <SidebarWithHeader>
                <DrawerForm
                    fetchCustomers={fetchCustomers}
                />
                <Text mt={5}>Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }

    if (customers.length <= 0) {
        return (
            <SidebarWithHeader>
                <DrawerForm
                    fetchCustomers={fetchCustomers}
                />
                <Text mt={5}>No customer found</Text>
            </SidebarWithHeader>
        )
    }

    return (
        <SidebarWithHeader>
            <CreateCustomerDrawer
                fetchCustomers={fetchCustomers}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {customers.map((customer, index) => (
                    <WrapItem key={index}>
                        <CardWithImage {...customer}
                                       imageNumber={index}
                                       fetchCustomers={fetchCustomers}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>
    )
}

// lesson386
export default App;