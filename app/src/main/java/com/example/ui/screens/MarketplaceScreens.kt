package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ProductItem
import com.example.ui.theme.AuraBg
import com.example.ui.theme.AuraCoral
import com.example.ui.theme.AuraCoralDark
import com.example.ui.theme.AuraCoralSoft
import com.example.ui.theme.AuraGold
import com.example.ui.theme.AuraMint
import com.example.ui.theme.AuraMintDark
import com.example.ui.theme.AuraSuccess
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.AuraSurface2
import com.example.ui.theme.AuraText
import com.example.ui.theme.AuraText2
import com.example.ui.theme.AuraText3

// SCREEN 37 — MARKETPLACE
@Composable
fun MarketplaceScreen(
    products: List<ProductItem>,
    onProductClick: (ProductItem) -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val filtered = products.filter {
        (selectedCategory == "All" || it.category.equals(selectedCategory, ignoreCase = true)) &&
            it.title.contains(searchQuery, ignoreCase = true)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("marketplace_screen"),
        color = AuraBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
                }
                Text("Marketplace", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = AuraText)
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search products, art, items...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = AuraText3) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AuraCoral,
                    unfocusedContainerColor = AuraSurface
                )
            )

            // Category filter pills
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Home", "Electronics", "Furniture", "Fashion").forEach { cat ->
                    item {
                        val isSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) AuraCoral else AuraSurface)
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) Color.White else AuraText2,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // 2-Column Product Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filtered) { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(3.dp, RoundedCornerShape(18.dp))
                            .clickable { onProductClick(product) },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = AuraSurface)
                    ) {
                        Column {
                            // Product preview box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp)
                                    .background(Color(product.cardColor)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("🛍️", fontSize = 42.sp)
                            }

                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "$${String.format("%.2f", product.price)}",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = AuraGold
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(AuraMint)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text("Escrow", fontSize = 9.sp, color = AuraMintDark, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = product.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = AuraText,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = AuraText3, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(product.location, fontSize = 11.sp, color = AuraText3, maxLines = 1)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// SCREEN 38 — PRODUCT DETAIL with Escrow Checkout Modal
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: ProductItem,
    walletBalance: Double,
    onBack: () -> Unit,
    onChatSeller: () -> Unit,
    onConfirmPurchase: (String) -> Boolean
) {
    var isSaved by remember { mutableStateOf(product.isSaved) }
    var showEscrowModal by remember { mutableStateOf(false) }
    var purchaseSuccess by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("product_detail_screen"),
        color = AuraBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
                    }
                    IconButton(onClick = { isSaved = !isSaved }) {
                        Icon(
                            imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Save",
                            tint = if (isSaved) AuraCoral else AuraText
                        )
                    }
                }

                // Big Image Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(Color(product.cardColor)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🛍️", fontSize = 80.sp)
                }

                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$${String.format("%.2f", product.price)}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = AuraGold
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(AuraMint)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Security, contentDescription = null, tint = AuraMintDark, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Aura Escrow Protected", fontSize = 11.sp, color = AuraMintDark, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = product.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AuraText
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = AuraCoral, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(product.location, color = AuraText2, fontSize = 13.sp)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("•", color = AuraText3)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Condition: ${product.condition}", color = AuraMintDark, fontWeight = FontWeight.Medium, fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Seller card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = AuraSurface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(AuraCoral),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = product.sellerName.take(2).uppercase(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(product.sellerName, fontWeight = FontWeight.Bold, color = AuraText, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(Icons.Default.CheckCircle, contentDescription = "Verified", tint = AuraMintDark, modifier = Modifier.size(14.dp))
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, contentDescription = "Rating", tint = AuraGold, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text("${product.rating} (${product.reviewsCount} reviews)", fontSize = 12.sp, color = AuraText2)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text("Description", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = AuraText)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = product.description,
                        fontSize = 14.sp,
                        color = AuraText2,
                        lineHeight = 22.sp
                    )
                }
            }

            // Bottom Actions (Buy Now & Chat)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onChatSeller,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AuraSurface),
                    shape = RoundedCornerShape(26.dp)
                ) {
                    Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Chat", tint = AuraCoral)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Chat", color = AuraCoral, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { showEscrowModal = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                    shape = RoundedCornerShape(26.dp)
                ) {
                    Icon(Icons.Default.ShoppingBag, contentDescription = "Buy", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Buy with Escrow", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Escrow Checkout Modal Bottom Sheet
        if (showEscrowModal) {
            ModalBottomSheet(
                onDismissRequest = {
                    showEscrowModal = false
                    purchaseSuccess = false
                },
                containerColor = AuraSurface,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    if (purchaseSuccess) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(AuraMint),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = "Success", tint = AuraMintDark, modifier = Modifier.size(36.dp))
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            Text("Escrow Order Created!", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AuraText)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Your payment of $${String.format("%.2f", product.price)} is held securely. Funds release to ${product.sellerName} once delivery is confirmed.", fontSize = 13.sp, color = AuraText2, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(
                                onClick = {
                                    showEscrowModal = false
                                    purchaseSuccess = false
                                    onBack()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.fillMaxWidth().height(48.dp)
                            ) {
                                Text("Done", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    } else {
                        Text("Aura Escrow Checkout", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AuraText)
                        Spacer(modifier = Modifier.height(14.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = AuraSurface2)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(product.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = AuraText)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Seller: ${product.sellerName}", fontSize = 12.sp, color = AuraText2)
                                Spacer(modifier = Modifier.height(12.dp))
                                HorizontalDivider(color = AuraText3.copy(alpha = 0.2f))
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Subtotal", color = AuraText2, fontSize = 13.sp)
                                    Text("$${String.format("%.2f", product.price)}", fontWeight = FontWeight.Bold, color = AuraText)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Escrow Protection Fee", color = AuraText2, fontSize = 13.sp)
                                    Text("Free", fontWeight = FontWeight.Bold, color = AuraSuccess)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Total", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = AuraText)
                                    Text("$${String.format("%.2f", product.price)}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = AuraGold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = AuraMintDark, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Your wallet balance: $${String.format("%.2f", walletBalance)}", fontSize = 12.sp, color = AuraMintDark, fontWeight = FontWeight.Medium)
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                val ok = onConfirmPurchase(product.id)
                                if (ok) purchaseSuccess = true
                            },
                            enabled = walletBalance >= product.price,
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            Text(
                                text = if (walletBalance >= product.price) "Confirm & Fund Escrow" else "Insufficient Wallet Balance",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
