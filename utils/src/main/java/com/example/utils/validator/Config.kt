package com.example.utils.validator

import java.util.regex.Pattern

object Config {
    const val EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+$"
    var emailPattern = Pattern.compile(EMAIL_PATTERN)
}