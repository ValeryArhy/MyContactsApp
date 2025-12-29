package com.example.contacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val contact = Contact(
                name = "Алексей",
                surname = "Игоревич",
                familyName = "Березин",
                isFavorite = true,
                phone = "+7 911 111 22 33",
                address = "г. Казань, ул. Мира, 10",
                email = "alex@test.com"
            )

            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ContactDetails(contact = contact)
                }
            }
        }
    }
}

@Composable
fun ContactDetails(contact: Contact) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactAvatar(contact)

        Spacer(modifier = Modifier.height(20.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))

            val fullName = listOfNotNull(contact.name, contact.surname, contact.familyName).joinToString(" ")
            Text(
                text = fullName,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            if (contact.isFavorite) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = android.R.drawable.star_big_on),
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(32.dp))


        Column(modifier = Modifier.fillMaxWidth()) {
            InfoRow(label = stringResource(R.string.phone), value = contact.phone)
            InfoRow(label = stringResource(R.string.address), value = contact.address)
            InfoRow(label = stringResource(R.string.email), value = contact.email)
        }
    }
}

@Composable
fun ContactAvatar(contact: Contact) {
    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        if (contact.imageRes != null) {
            Image(
                painter = painterResource(id = contact.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.circle),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                tint = Color.LightGray
            )
            val initials = "${contact.name.take(1)}${contact.familyName.take(1)}".uppercase()
            Text(
                text = initials,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun InfoRow(label: String, value: String?) {
    if (!value.isNullOrEmpty()) {
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                letterSpacing = 1.sp
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Preview(showBackground = true, name = "Избранный без фото")
@Composable
fun PreviewFavoriteNoPhoto() {
    val contact = Contact(
        name = "Иван",
        surname = "Иванович",
        familyName = "Иванов",
        isFavorite = true,
        phone = "+7 900 123 45 67",
        address = "Москва, ул. Ленина, 1",
        email = "ivanov@mail.ru"
    )
    ContactDetails(contact)
}

@Preview(showBackground = true, name = "Не избранный с фото")
@Composable
fun PreviewNormalWithPhoto() {
    val contact = Contact(
        name = "Мария",
        familyName = "Сидорова",
        imageRes = android.R.drawable.ic_menu_report_image,
        isFavorite = false,
        phone = "8 800 555 35 35",
        address = "г. Самара, ул. Садовая, 5",
        email = null
    )
    ContactDetails(contact)
}