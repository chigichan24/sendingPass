package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
	"net/url"
	"strings"
	"time"
)

func post(client *http.Client, values url.Values) {
	req, err := http.NewRequest("POST", "http://10.10.10.10/cgi-bin/Login.cgi", strings.NewReader(values.Encode()))
	if err != nil {
		fmt.Println(err)
		return
	}

	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")

	resp, err := client.Do(req)
	if err != nil {
		fmt.Println(err)
		return
	}
	defer resp.Body.Close()

	_, err = ioutil.ReadAll(resp.Body)
	if err == nil {
		fmt.Println("Success!")
	}

}

func main() {
	values := url.Values{}
	values.Add("uid", "USERNAME")
	values.Add("pwd", "PASSWORD")

	client := &http.Client{Timeout: time.Duration(10 * time.Second)}

	post(client, values)
}
