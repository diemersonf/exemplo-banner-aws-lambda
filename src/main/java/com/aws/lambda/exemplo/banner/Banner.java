package com.aws.lambda.exemplo.banner;

import java.util.Map;
import java.util.Objects;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "exemplo-banner")
public class Banner {
	@DynamoDBHashKey(attributeName="nome")
	private String nome;
	
	@DynamoDBAttribute(attributeName = "link")
	private String link;
	
	@DynamoDBAttribute(attributeName = "texto")
	private String texto;
	
	@DynamoDBAttribute(attributeName = "acao")
	private String acao;
	
	@DynamoDBAttribute(attributeName = "tags")
	private Map<String, String> tags;
	
	public Banner() {
		super();
	}

	public Banner(String nome, String link, String texto, String acao, Map<String, String> tags) {
		super();
		this.nome = nome;
		this.link = link;
		this.texto = texto;
		this.acao = acao;
		this.tags = tags;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		return Objects.hash(acao, link, nome, tags, texto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Banner other = (Banner) obj;
		return Objects.equals(acao, other.acao) && Objects.equals(link, other.link) && Objects.equals(nome, other.nome)
				&& Objects.equals(tags, other.tags) && Objects.equals(texto, other.texto);
	}

	@Override
	public String toString() {
		return "Banner [nome=" + nome + ", link=" + link + ", texto=" + texto + ", acao=" + acao + ", tags=" + tags
				+ "]";
	}
}
